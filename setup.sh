#!/bin/bash

rm -rf /home/server

(cd lab1 && bash setup.sh)
(cd lab2_soap && bash setup.sh)
(cd lab2_rest && bash setup.sh)

printf "#!/bin/bash\n(cd rmi_server && bash runserver.sh) & \n(cd soap_server && bash runserver.sh) & \n(cd rest_server && bash runserver.sh) &" >> "/home/server/runserver.sh"

