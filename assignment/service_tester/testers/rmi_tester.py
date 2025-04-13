import subprocess
import time


def test_rmi(host: str):
    start = time.perf_counter_ns()
    try:
        subprocess.run(["./rmi", host], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        success = True
    except subprocess.CalledProcessError:
        success = False
    return time.perf_counter_ns() - start, success
