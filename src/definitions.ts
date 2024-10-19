export interface AppfueledTwilioCapacitorPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
