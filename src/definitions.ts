export interface AppfueledTwilioCapacitorPlugin {
  // Method to register the device for handling incoming calls
  registerIncomingCallConnection(options: { token: string }): Promise<void>;

  // Method to register the device for handling outgoing calls
  registerOutgoingCallConnection(options: { token: string }): Promise<void>;

  // Method to accept an incoming call
  acceptIncomingCallConnection(): Promise<void>;

  // Method to reject an incoming call
  rejectIncomingCallConnection(): Promise<void>;

  // Method to initiate an outgoing call
  startOutgoingCallConnection(options: { phoneNumber: string }): Promise<void>;

  // Method to terminate an outgoing call
  terminateOutgoingCallConnection(): Promise<void>;
}
