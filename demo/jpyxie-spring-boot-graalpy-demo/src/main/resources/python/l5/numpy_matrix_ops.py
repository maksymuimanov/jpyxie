import numpy as np

integer = java{integer}

# create two large matrices
A = np.random.rand(100 + integer % 50, 100 + integer % 50)
B = np.random.rand(A.shape[0], A.shape[1])

# multiple heavy array operations
C = A @ B         # matrix multiply
eigs = np.linalg.eigvals(C)

sum_eigs = np.sum(eigs)
mean_val = np.mean(C)
max_val = np.max(C)
min_val = np.min(C)

result = f"shape={C.shape}, mean={mean_val:.5f}, min={min_val:.5f}, max={max_val:.5f}, sum_eigs={sum_eigs:.5f}"