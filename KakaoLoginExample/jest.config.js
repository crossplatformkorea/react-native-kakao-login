/* eslint-disable @typescript-eslint/no-var-requires */
const {defaults: tsjPreset} = require('ts-jest/presets');
/* eslint-enable @typescript-eslint/no-var-requires */

module.exports = {
  ...tsjPreset,
  preset: 'react-native',
  automock: false,
  transform: {
    '^.+\\.(js)$': '<rootDir>/node_modules/babel-jest',
    '\\.ts$': 'ts-jest',
  },
  modulePaths: ['<rootDir>'],
  moduleDirectories: ['node_modules'],
  moduleFileExtensions: ['ts', 'tsx', 'js', 'svg', 'png', 'json'],
  globals: {
    'ts-jest': {
      babelConfig: true,
    },
  },
  modulePathIgnorePatterns: [
    '<rootDir>/build/',
    '<rootDir>/node_modules/',
    '<rootDir>/.history/',
  ],
  // 'testRegex': '(/__tests__/.*|(\\.|/)(test|spec))\\.(jsx?|tsx?)$',
  cacheDirectory: '.jest/cache',
  setupFilesAfterEnv: ['./test/setupTest.ts'],
  moduleNameMapper: {
    '\\.svg': '<rootDir>/__mocks__/svgMock.js',
    '.+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$': 'babel-jest',
  },
  setupFiles: [
    './node_modules/react-native-gesture-handler/jestSetup.js',
    '<rootDir>/test/jestSetup.js',
    '<rootDir>/test/jestSetup.ts',
  ],
  testRegex: '(/__tests__/.*|\\.(test|spec))\\.(ts|tsx|js)$',
  testPathIgnorePatterns: ['\\.snap$', '<rootDir>/node_modules/'],
  /* eslint-disable */
  transformIgnorePatterns: [
    'node_modules/(?!(jest-)?(@react-native|react-native)|react-clone-referenced-element|@react-native-community|@unimodules|expo(nent)?|@expo(nent)?/.*|react-navigation|@react-navigation/.*|@unimodules/.*|unimodules-*|native-base|dooboo-ui/*|@dooboo-ui|@sentry/.*|sentry-expo)',
  ],
  /* eslint-enable */
  haste: {
    defaultPlatform: 'ios',
    platforms: ['android', 'ios', 'native'],
  },
  coveragePathIgnorePatterns: ['/node_modules/'],
};
