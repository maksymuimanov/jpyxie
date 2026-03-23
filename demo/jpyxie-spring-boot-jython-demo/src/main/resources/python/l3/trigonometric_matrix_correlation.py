import math

integer = java{integer}

size = 30

matrix = []
for i in range(size):
    row = []
    for j in range(size):
        val = math.sin(i * j + integer) + math.cos(i + j)
        row.append(val)
    matrix.append(row)

score = 0

for i in range(size):
    for j in range(size):
        score += matrix[i][j] * matrix[j][i]

result = "matrix_score={0}".format(score)