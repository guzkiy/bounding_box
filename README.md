# Take Home project

## General notes
Assumptions:
1. The program returns **the largest** by area bounding box as described in the project description in 'BoundingBox.md'.
There might be slight misunderstanding of the project description on my side, as there are slightly unclear statements in the description mentioning multiple boxes as well as the largest, as follows:
   - "Of all the non-overlapping, minimum bounding boxes in the input, __it should be the largest.__"
   - "If any boxes satisfying the conditions can be found ... the program should ... __for each box__, print a line to stdout with ... coordinates"
2. The one-cell minimal bounding boxes are considered
3. The largest box is the one with the biggest area 

The idea of the algorithm is as follows.
1. we can think of the input as a matrix of Booleans where grid(x,y) == true for the cells with asterisks 
2. We can think of connected (horizontally or vertically) cells with asterisks as nodes in a graph 
3. In order to separate the group of cells with asterisks a graph algorithm finding 'connected components' can be utilized. 
   The DFS function in the 'connected components' algorithm has been implemented to return the minimal bounding box for the group. 
   The 'connected components' algorithm returns a set of minimal bounding boxes - one for each separate group of nodes (i.e. components).
4. The set of bounding boxes is analysed for the intersections and overlapping bounding boxes are filtered out from the set.
5. From the remaining set of bounding boxes the largest by area is taken.

## Building and running instructions
The project is build/run with standard SBT and sbt-native-packager plugin, 
the plugin can build a runnable application package and a shell script to start the application. 
To build all the targets please run SBT in the project directory as follows:
~~~
sbt clean package stage universal:packageZipTarball universal:packageBin
~~~
Running the application:
There is a series of test files in the ./testdata directory
Please run the application built on a previous stage as follows
~~~
$ cat ./testdata/test3.txt | ./target/universal/stage/bin/bounding-box
(1,1)(2,2)

~~~
Tests can be run as follows
~~~
sbt test
~~~
The expected output is as follows:
~~~
$ sbt clean package stage universal:packageBin
[info] welcome to sbt 1.4.7 (Ubuntu Java 11.0.10)
[info] loading global plugins from /home/.../.sbt/1.0/plugins
[info] loading settings for project bounding-box-build from plugins.sbt ...
[info] loading project definition from /home/.../IdeaProjects/bounding-box/project
[info] loading settings for project bounding-box from build.sbt ...
[info] set current project to bounding-box (in build file:/home/.../IdeaProjects/bounding-box/)
[success] Total time: 0 s, completed Apr 24, 2021, 2:38:41 PM
[info] compiling 1 Scala source to /home/.../IdeaProjects/bounding-box/target/scala-2.13/classes ...
[success] Total time: 3 s, completed Apr 24, 2021, 2:38:44 PM
[info] Main Scala API documentation to /home/.../IdeaProjects/bounding-box/target/scala-2.13/api...
[info] Wrote /home/guzkiy/IdeaProjects/bounding-box/target/scala-2.13/bounding-box_2.13-0.1.pom
[info] Main Scala API documentation successful.
[success] Total time: 1 s, completed Apr 24, 2021, 2:38:45 PM
[info] Wrote /home/.../IdeaProjects/bounding-box/target/scala-2.13/bounding-box_2.13-0.1.pom
[info] Main Scala API documentation to /home/.../IdeaProjects/bounding-box/target/scala-2.13/api...
[info] Main Scala API documentation successful.
[warn] [1] The maintainer is empty
[warn] Add this to your build.sbt
[warn]   maintainer := "your.name@company.org"
[success] All package validations passed
[success] Total time: 1 s, completed Apr 24, 2021, 2:38:46 PM

~~~
