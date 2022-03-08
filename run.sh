

export ZEEBE_CLUSTER_ID=75d90105-f263-4f5c-b4f2-dd84d7cc52f5
export ZEEBE_CLIENT_ID=lO~ivuwZ0pjadc-T18jvZIzE0yDuWpiN
export ZEEBE_CLIENT_SECRET=CNFzxPgYVsoQJ5P4pmrPilT69q0jNU-zgCQo-FSbZkNal0UKla7Dw9vetLGnYyHx


echo "building... "
rm -rf ./build
./gradlew build




echo "starting... "
java -jar ./build/libs/camunda-workers-demo-*-all.jar
