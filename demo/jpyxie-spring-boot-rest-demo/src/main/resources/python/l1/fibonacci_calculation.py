integer = java{integer}
text = java{text}

def fibonacci(n):
    if n <= 1:
        return n
    a, b = 0, 1
    for _ in range(n):
        a, b = b, a + b
    return a

n = 20 + (integer % 10)
fib = fibonacci(n)

length = len(text)
score = fib + length

result = f"fib({n})={fib},score={score}"