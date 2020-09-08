let functions = require('firebase-functions');

let admin = require('firebase-admin');

admin.initializeApp();

exports.sendNotification = functions.database.ref('/notifications/{notificationId}').onWrite((change, context) =>{
    
    //get the userId of the person receiving the notification because we need to get their token
    const receiverId = 'abcd';
    console.log("receiverId:", receiverId);

    //get the message
    const message = change.after.child('message').val();
    const type = change.after.child('type').val();
    console.log("message: ", message);

    const messageId = context.params.notificationId;
    console.log("messageId: ", messageId);

    return admin.database().ref("/messageTokens/" + receiverId).once('value').then(snap => {
        const token = snap.val();
        console.log("token: ", token);

        //building the message payload and send the notification
        const payload = {
            data: {
                data_type: "direct_message",
                title: "New Message",
                type: type,
                message: message,
                message_id: messageId,
            }
        };

        return admin.messaging().sendToDevice(token, payload)
                    .then(function(response){
                        console.log("Successfully sent message: ", response);
                        return response.successCount;
                    })
                    .catch(function(error){
                        console.log("Error sending message: ", error)
                    });
    });
});