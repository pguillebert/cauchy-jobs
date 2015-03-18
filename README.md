# cauchy-jobs-kestrel

This is an example of a plugin for the [Cauchy](https://github.com/pguillebert/cauchy)
monitoring agent.

It checks the [Kestrel](http://twitter.github.io/kestrel/) queueing server.

It provides the following metrics for every queue on the server :

* age (oldest queued item, in milliseconds)
* items (number of items waiting in the queue)
* put_rate (average number of events queued, per second, last period (default 1h)
* get_rate (average number of events dequeued, per second, last period (default 1h)

## Usage

Just add the JAR to your classpath, add the provided profile configuration
to your cauchy configuration.

## License

Copyright © 2015 Philippe Guillebert

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
