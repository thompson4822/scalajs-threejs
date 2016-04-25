# Example Scala.js Three.js application 

This is an experimental marraige of Scala.js and Three.js. I'm still learning both, and would like
an environment in which I could bring the two technologies together.

## Get started

To get started, run `sbt ~fastOptJS` in this example project. This should download dependencies and 
prepare the relevant javascript files. If you open `localhost:12345/target/scala-2.11/classes/index-dev.html` 
in your browser, it will show you whatever has been created in the technologies so far. You can then
edit the application and see the updates be sent live to the browser without needing to refresh the page.

## The optimized version

Run `sbt fullOptJS` and open up `index-opt.html` for an optimized (~200kb) version
of the final application, useful for final publication.

