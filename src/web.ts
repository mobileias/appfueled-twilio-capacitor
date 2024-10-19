import { WebPlugin } from '@capacitor/core';

import type { AppfueledTwilioCapacitorPlugin } from './definitions';

export class AppfueledTwilioCapacitorWeb extends WebPlugin implements AppfueledTwilioCapacitorPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
