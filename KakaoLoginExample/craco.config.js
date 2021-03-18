module.exports = {
  babel: {
    presets: ['@babel/preset-typescript'],
    plugins: [
      [
        'babel-plugin-fbt',
        {
          fbtEnumManifest: require('./src/utils/i18n/fbt/.enum_manifest.json'),
          extraOptions: {__self: true},
        },
      ],
      'babel-plugin-fbt-runtime',
    ],
  },
};
