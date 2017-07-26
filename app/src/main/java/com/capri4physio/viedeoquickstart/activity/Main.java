package com.capri4physio.viedeoquickstart.activity;/*
package com.twilio.video.quickstart.activity;

*/
/**
 * Created by emobi8 on 2/15/2017.
 *//*


    public class Main {

        // Substitute your Twilio AccountSid and ApiKey details
        public static final String ACCOUNT_SID = "ACCOUNT_SID";
        public static final String API_KEY_SID = "API_KEY_SID";
        public static final String API_KEY_SECRET = "API_KEY_SECRET";

        public static void main(String[] args) throws Exception {
            // Create a ConversationsGrant
            ConversationsGrant grant = new ConversationsGrant();
            grant.setConfigurationProfileSid("configurationProfileSid");

            // Create an Access Token
            AccessToken token = new AccessToken.Builder(ACCOUNT_SID, API_KEY_SID, API_KEY_SECRET)
                    .identity("example-user") // Set the Identity of this token
                    .grant(grant) // Grant access to Conversations
                    .build();

            // Serialize the token as a JWT
            String jwt = token.toJWT();
            System.out.println(jwt);
        }

    }
*/
