integer = java{integer}

def is_prime(n):
    if n < 2:
        return False
    i = 2
    while i * i <= n:
        if n % i == 0:
            return False
        i += 1
    return True

limit = 100 + integer * 5
primes = []

for i in range(limit):
    if is_prime(i):
        primes.append(i)

result = f"primes_found={len(primes)}"