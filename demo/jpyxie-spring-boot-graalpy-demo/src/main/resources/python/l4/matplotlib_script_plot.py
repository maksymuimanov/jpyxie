import numpy as np
import matplotlib.pyplot as plt

integer = java{integer}

x = np.linspace(0, 4*np.pi, 5000)
y = np.sin(x * (integer%5 + 1)) + np.cos(x * (integer%7 + 1))

plt.plot(x, y)
plt.title("wave")
plt.xlabel("x")
plt.ylabel("y")
plt.savefig("/tmp/plot.png")
plt.close()

result = "matplotlib_wave_plot_saved"