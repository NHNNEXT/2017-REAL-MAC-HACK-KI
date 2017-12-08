const functions = require('firebase-functions');
const admin = require('firebase-admin');
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
// //

admin.initializeApp(functions.config().firebase);

exports.notify = functions.database.ref('/messages/{roomId}/{messageId}/email')
    .onWrite(event => {

        const roomId = event.params.roomId;
        const messageId = event.params.messageId;
        const email = event.params.email;

        console.log('roomId', roomId);


        const userKeysPromise = admin.database().ref(`/rooms/${roomId}`).once('value');

        console.log(userKeysPromise);

        const userId = Object.keys(userKeysPromise.val());

        console.log(userId);

        const getDeviceTokens = admin.database().ref(`/users/${userId}/deviceKey`).once('value');

        return Promise.all([getDeviceTokens]).then(results => {
            const tokenSnapshot = results[0];

            const tokens = Object.keys(tokenSnapshot.val());


            const payload = {
                notification: {
                    title: "new message",
                    body: "new message"
                }
            };


            return admin.messaging().sendToDevice(tokens, payload).then(response => {
                // For each message check if there was an error.
                const tokensToRemove = [];
                response.results.forEach((result, index) => {
                    const error = result.error;
                    if (error) {
                        console.error('Failure sending notification to', tokens[index], error);
                        // Cleanup the tokens who are not registered anymore.
                        if (error.code === 'messaging/invalid-registration-token' ||
                            error.code === 'messaging/registration-token-not-registered') {
                            tokensToRemove.push(tokenSnapshot.ref.child(tokens[index]).remove());
                        }
                    }
                });
                return Promise.all(tokensToRemove);
            });
        });

    });
