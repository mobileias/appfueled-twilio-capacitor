import { registerPlugin } from '@capacitor/core';
import type { AppfueledTwilioCapacitorPlugin } from './definitions';

// Register the plugin with Capacitor, providing fallback to web implementation if needed
const AppfueledTwilioCapacitor = registerPlugin<AppfueledTwilioCapacitorPlugin>('AppfueledTwilioCapacitor', {
  web: () => import('./web').then((m) => new m.AppfueledTwilioCapacitorWeb()),
});

// Export the definitions and the plugin itself
export * from './definitions';
export { AppfueledTwilioCapacitor };
