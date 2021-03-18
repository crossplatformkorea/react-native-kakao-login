import {NativeModules, Platform} from 'react-native';

import {init} from 'fbt';
import intl from './i18n/fbt/translatedFbts.json';

const DEFAULT_LOCALE = 'en_US';

export const viewerContext = {
  locale: DEFAULT_LOCALE,
};

export const initFbt = (): void => {
  const deviceLanguage =
    Platform.OS === 'ios'
      ? NativeModules?.SettingsManager?.settings?.AppleLocale ||
        NativeModules?.SettingsManager?.settings?.AppleLanguages[0] // iOS 13
      : NativeModules?.I18nManager?.localeIdentifier;

  if (deviceLanguage)
    if (deviceLanguage === 'en') viewerContext.locale = 'en_US';
    else if (deviceLanguage === 'ko') viewerContext.locale = 'ko_KR';
    else viewerContext.locale = 'en_US';

  if (Platform.OS === 'web')
    viewerContext.locale =
      navigator.language.replace('-', '_') ?? DEFAULT_LOCALE;

  init({
    translations: intl,
    hooks: {
      getViewerContext: (): {locale: string} => viewerContext,
    },
  });
};

export const LOCALES = Object.freeze({
  ko: Object.freeze({
    bcp47: 'ko',
    rtl: false,
  }),
  en: Object.freeze({
    bcp47: 'en',
    rtl: false,
  }),
});

export const changeLocale = (locale: string): void => {
  viewerContext.locale = locale;
};
