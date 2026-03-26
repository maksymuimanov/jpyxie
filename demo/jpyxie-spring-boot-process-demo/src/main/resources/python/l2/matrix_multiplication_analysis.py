integer = java{integer}

def matrix_multiply(a, b):
    result = []
    size = len(a)

    for i in range(size):
        row = []
        for j in range(size):
            s = 0
            for k in range(size):
                s += a[i][k] * b[k][j]
            row.append(s)
        result.append(row)

    return result

size = 10 + integer % 5

A = [[(i + j) % 5 for j in range(size)] for i in range(size)]
B = [[(i * j) % 7 for j in range(size)] for i in range(size)]

C = matrix_multiply(A, B)

diag = sum(C[i][i] for i in range(size))

result = f"matrix_size={size},diag={diag}"
print(result)