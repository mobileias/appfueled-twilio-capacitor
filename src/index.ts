import { registerPlugin } from '@capacitor/core';

import type { AppfueledTwilioCapacitorPlugin } from './definitions';

const AppfueledTwilioCapacitor = registerPlugin<AppfueledTwilioCapacitorPlugin>('AppfueledTwilioCapacitor', {
  web: () => import('./web').then((m) => new m.AppfueledTwilioCapacitorWeb()),
});

export * from './definitions';
export { AppfueledTwilioCapacitor };
