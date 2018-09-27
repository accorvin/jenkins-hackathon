Jenkins configuration
=====================
This directory contains a number of Groovy files which initialize/configure different
parts of Jenkins during the start up.

The files are supposed to be independent and the order of the execution should not matter.
In practice, Jenkins will execute the files in alphabetical order.
