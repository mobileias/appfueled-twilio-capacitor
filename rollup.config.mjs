export default {
  input: 'dist/esm/index.js',
  output: [
    {
      file: 'dist/plugin.js',
      format: 'iife',
      name: 'capacitorAppfueledTwilioCapacitor',
      globals: {
        '@capacitor/core': 'capacitorExports',
        '@twilio/voice-sdk': 'TwilioVoiceSDK',  // Specify Twilio Voice SDK as global variable
      },
      sourcemap: true,
      inlineDynamicImports: true,
    },
    {
      file: 'dist/plugin.cjs.js',
      format: 'cjs',
      sourcemap: true,
      inlineDynamicImports: true,
    },
  ],
  external: ['@capacitor/core', '@twilio/voice-sdk'],  // Treat Twilio Voice SDK as external
};
