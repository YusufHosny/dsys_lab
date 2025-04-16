import json
from testers import rmi_tester, rest_tester, soap_tester
from utils.runner import run_test
import argparse


def main(args):
    testers = {
        'rmi': rmi_tester.test_rmi,
        'soap': soap_tester.test_soap,
        'rest': rest_tester.test_rest,
        'restrpc': rest_tester.test_restrpc,
    }

    run_test(testers[args.type],
             (args.url,),
             args.clients,
             args.rps,
             args.length,
             args.outfile
             )


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('clients', help='number of parallel clients to launch', type=int)
    parser.add_argument('rps', help='requests per second', type=int)
    parser.add_argument('length', help='time in seconds to run tests for', type=int)
    parser.add_argument('type', help='which service to hit (rmi, soap, rest, restrpc)', type=str)
    parser.add_argument('url', help='url of vm to hit', type=str)
    parser.add_argument('--outfile', '-o', help='file to put results in', type=str)
    args = parser.parse_args()
    main(args)
