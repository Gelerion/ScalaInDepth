Eventual Consistency
As soon as a system scales beyond a single machine we have to make a fundamental choice about how we manage data.

One approach is to build a system that is consistent, meaning that all machines have the same view of data.
For example, if a user changes their password then all machines that store a copy of that password must accept
the change before we consider the operation to have completed successfully.

Consistent systems are easy to work with but they have their disadvantages. They tend to have high latency
because a single change can result in many messages being sent between machines. They also tend to have relatively
low uptime because outages can cut communications between machines creating a network partition. When there
is a network partition, a consistent system may refuse further updates to prevent inconsistencies across machines.

An alternative approach is an eventually consistent system. This means that at any particular point in time machines
are allowed to have differing views of data. However, if all machines can communicate and there are no further
updates they will eventually all have the same view of data.

Eventually consistent systems require less communication between machines so latency can be lower.
A partitioned machine can still accept updates and reconcile its changes when the network is fixed,
so systems can also have better uptime.

The big question is: how do we do this reconciliation between machines? CRDTs provide one approach to the problem.

The GCounter
Let’s look at one particular CRDT implementation. Then we’ll attempt to generalise properties to see if we
can find a general pattern.

The data structure we will look at is called a GCounter. It is a distributed increment-only counter that can be used,
for example, to count the number of visitors to a web site where requests are served by many web servers.

To see why a straightforward counter won’t work, imagine we have two servers storing a simple count of visitors.
Let’s call the machines A and B. Each machine is storing an integer counter and the counters all start at zero.

 Machine A  Machine B
 |   0    | |   0   |

Now imagine we receive some web traffic. Our load balancer distributes five incoming requests to A and B, A serving
three visitors and B two. The machines have inconsistent views of the system state that they need to reconcile
to achieve consistency. One reconciliation strategy with simple counters is to exchange counts and add them

  Machine A  Machine B
  |   3    | |   2   |
      \         /      add counters
  Machine A  Machine B
  |   5    | |   5   |

So far so good, but things will start to fall apart shortly. Suppose A serves a single visitor, which means
we’ve seen six visitors in total.

  Machine A  Machine B
  |   6    | |   5   |
      \         /      add counters
  Machine A  Machine B
  |   11   | |   11  |

This is clearly wrong! The problem is that simple counters don’t give us enough information about the history of
interactions between the machines. Fortunately we don’t need to store the complete history to get the correct answer—just a summary of it.

GCounters
The first clever idea in the GCounter is to have each machine storing a separate counter for every machine it knows
about (including itself). In the previous example we had two machines, A and B. In this situation both machines
would store a counter for A and a counter for B

  Machine A   Machine B
  |   A:0  | |   A:0  |
  |   B:0  | |   B:0  |

The rule with GCounters is that a given machine is only allowed to increment its own counter.
If A serves three visitors and B serves two visitors the counters look as shown

  Machine A   Machine B
  |   A:3  | |   A:0  |
  |   B:0  | |   B:2  |

When two machines reconcile their counters the rule is to take the largest value stored for each machine.
In our example, the result of the first merge will be as shown

  Machine A   Machine B
  |   A:3  | |   A:0  |
  |   B:0  | |   B:2  |
      \         /      add counters
  Machine A   Machine B
  |   A:3  | |   A:3  |
  |   B:2  | |   B:2  |

Subsequent incoming web requests are handled using the increment-own-counter rule and subsequent merges are handled
using the take-maximum-value rule, producing the same correct values for each machine as shown

  Machine A   Machine B
  |   A:4  | |   A:0  |
  |   B:0  | |   B:2  |
      \         /      add counters
  Machine A   Machine B
  |   A:4  | |   A:4  |  -> A:4 - correct result
  |   B:2  | |   B:2  |















