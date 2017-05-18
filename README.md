# Performance analysis
The following tables show approximate amount of time spent on tree operations. All tests were performed using JUnit 5 testing framework. *The time is shown in milliseconds*.

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


## B-tree

t = 100

| Elements | Sequential Insertion | Sequential Search | Random Insertion | Random Search |
|:--------:|:--------------------:|:-----------------:|:----------------:|:-------------:|
| 100      |          20          |         1         |        25        |       1       |
| 1000     |          50          |         1         |        40        |       1       |
| 10000    |          140         |         3         |        100       |       8       |
| 100000   |          300         |         20        |        300       |       20      |
| 1000000  |          1200        |         35        |       2200       |       25      |
| 10000000 |          9500        |        130        |       19000      |       450     |
