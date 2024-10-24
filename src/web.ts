import { WebPlugin } from '@capacitor/core';
import type { AppfueledTwilioCapacitorPlugin } from './definitions';
import { Device } from '@twilio/voice-sdk';  // Import only Device from Twilio Voice SDK

export class AppfueledTwilioCapacitorWeb extends WebPlugin implements AppfueledTwilioCapacitorPlugin {

  private incomingCallConnection: any = null;  // Incoming call connection
  private outgoingCallConnection: any = null;  // Outgoing call connection
  private device: Device | null = null;  // Twilio Device instance

  // Helper function to send logs to the main Capacitor app
  private sendLogToMainApp(message: string) {
    console.log(message);
    this.notifyListeners('log', { message });
  }

  // Helper function to send errors to the main Capacitor app
  private sendErrorToMainApp(error: string) {
    console.error(error);
    this.notifyListeners('error', { error });
  }

  // Method to register the Twilio device for handling incoming calls
  async registerIncomingCallConnection(options: { token: string }): Promise<void> {
    const { token } = options;

    try {
      this.device = new Device(token);  // Initialize the device with the token

      // Event listener for when the device is ready
      this.device.on('ready', () => {
        this.sendLogToMainApp('Twilio device ready for incoming calls.');
      });

      // Event listener for incoming call
      this.device.on('incoming', (connection) => {
        this.sendLogToMainApp('Incoming call detected.');
        this.incomingCallConnection = connection;  // Store the incoming connection
      });

      // Event listener for errors
      this.device.on('error', (error: any) => {
        this.sendErrorToMainApp('Twilio device error: ' + error);
      });
    } catch (error) {
      this.sendErrorToMainApp('Error registering for incoming calls on web: ' + error);
      throw new Error(`Failed to register device for incoming calls: ${error}`);
    }
  }

  // Method to accept an incoming call
  async acceptIncomingCallConnection(): Promise<void> {
    if (this.incomingCallConnection) {
      try {
        this.incomingCallConnection.accept();  // Accept the incoming call
        this.sendLogToMainApp('Incoming call accepted.');
      } catch (error) {
        this.sendErrorToMainApp('Error accepting incoming call: ' + error);
      }
    } else {
      this.sendErrorToMainApp('No incoming call to accept.');
    }
  }

  // Method to reject an incoming call
  async rejectIncomingCallConnection(): Promise<void> {
    if (this.incomingCallConnection) {
      try {
        this.incomingCallConnection.reject();  // Reject the incoming call
        this.sendLogToMainApp('Incoming call rejected.');
      } catch (error) {
        this.sendErrorToMainApp('Error rejecting incoming call: ' + error);
      }
    } else {
      this.sendErrorToMainApp('No incoming call to reject.');
    }
  }

  // Method to register the Twilio device for outgoing calls
  async registerOutgoingCallConnection(options: { token: string }): Promise<void> {
    const { token } = options;

    try {
      this.device = new Device(token);  // Initialize the device with the token

      // Event listener for when the device is ready
      this.device.on('ready', () => {
        this.sendLogToMainApp('Twilio device ready for outgoing calls.');
      });

      // Event listener for errors
      this.device.on('error', (error: any) => {
        this.sendErrorToMainApp('Twilio device error: ' + error);
      });
    } catch (error) {
      this.sendErrorToMainApp('Error registering for outgoing calls on web: ' + error);
      throw new Error(`Failed to register device for outgoing calls: ${error}`);
    }
  }

  // Method to initiate an outgoing call with dynamic options
  async startOutgoingCallConnection(options: { phoneNumber: string, options: { [key: string]: any } }): Promise<void> {
    const { phoneNumber, options: dynamicOptions } = options;

    if (this.device) {
      try {
        const params: { [key: string]: string } = { To: phoneNumber };

        // Merge any dynamic options into the params
        Object.keys(dynamicOptions).forEach((key) => {
          params[key] = dynamicOptions[key];
        });

        this.outgoingCallConnection = this.device.connect({
          params,  // Pass phone number and dynamic options
        });  // Make an outgoing call
        this.sendLogToMainApp(`Outgoing call initiated to ${phoneNumber} with options.`);
      } catch (error) {
        this.sendErrorToMainApp('Error initiating outgoing call: ' + error);
      }
    } else {
      this.sendErrorToMainApp('Twilio device is not registered for outgoing calls.');
    }
  }

  // Method to terminate an outgoing call
  async terminateOutgoingCallConnection(): Promise<void> {
    if (this.outgoingCallConnection) {
      try {
        this.outgoingCallConnection.disconnect();  // Disconnect the outgoing call
        this.sendLogToMainApp('Outgoing call terminated.');
      } catch (error) {
        this.sendErrorToMainApp('Error terminating outgoing call: ' + error);
      }
    } else {
      this.sendErrorToMainApp('No outgoing call to terminate.');
    }
  }
}
