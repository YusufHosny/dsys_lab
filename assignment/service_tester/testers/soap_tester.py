import os

import requests
import time
from urllib.parse import urlunparse

PORT = 1337


def test_soap(url: str):
    url = urlunparse(('http', f'{url}:{PORT}', 'soap/ws/', '', '', ''))
    headers = {'Content-Type': 'text/xml'}

    payloads = []
    for file in os.listdir('../soap/'):
        with open(f'../soap/{file}', 'r') as f:
            payloads += [f.read()]
    start = time.perf_counter_ns()
    try:
        for payload in payloads:
            response = requests.post(url, data=payload, headers=headers)
        return time.perf_counter_ns() - start, response.status_code == 200
    except requests.RequestException:
        return time.perf_counter_ns() - start, False
