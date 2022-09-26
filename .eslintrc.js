module.exports = {
  root: true,
  extends: '@dooboo/eslint-config-react-native',
  settings: {
    react: {version: 'detect'},
    jest: {version: 26},
  },
  parserOptions: {
    requireConfigFile: false,
  },
};
