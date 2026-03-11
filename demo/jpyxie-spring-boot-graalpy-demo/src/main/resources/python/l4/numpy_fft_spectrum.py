import numpy as np

integer = java{integer}

# generate signal
n = 1 << 14
x = np.linspace(0, 2 * np.pi, n)
signal = np.sin(5 * x) + 0.5 * np.sin(20 * x + integer)

spectrum = np.fft.fft(signal)
power = np.abs(spectrum)

max_power = np.max(power)
mean_power = np.mean(power)

result = f"len={len(power)},mean_pow={mean_power:.5f},max_pow={max_power:.5f}"