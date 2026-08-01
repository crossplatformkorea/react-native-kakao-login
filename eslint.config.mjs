import reactNativeConfig from '@react-native/eslint-config/flat';

export default [
  {
    ignores: [
      'node_modules/**',
      'KakaoLoginExample/**',
      // Build outputs (see .gitignore "Built files")
      'src/**/*.js',
      'src/**/*.d.ts',
      'src/**/*.js.flow',
      'plugins/**/*.js',
      'plugins/**/*.d.ts',
    ],
  },
  ...reactNativeConfig,
  {
    settings: {
      react: {version: 'detect'},
    },
  },
];
