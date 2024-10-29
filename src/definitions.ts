export interface AppfueledTwilioCapacitorPlugin {
  // Method to register the device for handling incoming calls
  registerIncomingCallConnection(options: { token: string }): Promise<void>;

  // Method to register the device for handling outgoing calls
  registerOutgoingCallConnection(options: { token: string }): Promise<void>;

  // Method to accept an incoming call
  acceptIncomingCallConnection(): Promise<void>;

  // Method to reject an incoming call
  rejectIncomingCallConnection(): Promise<void>;

  // Method to initiate an outgoing call with phoneNumber and dynamic options
  startOutgoingCallConnection(options: { phoneNumber: string, options: { [key: string]: any } }): Promise<void>;

  // Method to terminate an outgoing call
  terminateOutgoingCallConnection(): Promise<void>;

  // New method to toggle speaker on or off
  toggleSpeaker(options: { enable: boolean }): Promise<void>;
}
