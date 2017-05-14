# Performance analysis
The following table shows amount of time spent on tree operations. All tests were performed using JUnit 5 testing framework. *The time is shown in milliseconds*.

## Binary search tree

| Elements | Sequential Insertion | Sequential Search | Random Insertion | Random Search |
|:--------:|:--------------------:|:-----------------:|:----------------:|:-------------:|
| 100      |          20          |         1         |        20        |       1       |
| 1000     |          40          |         2         |        25        |       2       |
| 10000    |          270         |         6         |        40        |       7       |
| 100000   |         22000        |         8         |        150       |       15      |
| 1000000  |           -          |         -         |       1800       |       20      |
| 10000000 |           -          |         -         |       25000      |      800      |


## Red-black tree

| Elements | Sequential Insertion | Sequential Search | Random Insertion | Random Search |
|:--------:|:--------------------:|:-----------------:|:----------------:|:-------------:|
| 100      |          10          |         1         |         5        |       1       |
| 1000     |          20          |         1         |        20        |       1       |
| 10000    |          40          |         4         |        30        |       5       |
| 100000   |          50          |         7         |        120       |       15      |
| 1000000  |          500         |         20        |       1200       |       20      |
| 10000000 |         14000        |        500        |       22000      |      800      |

