interface Locale {
  countryCode: string;
  languageTag: string;
  languageCode: string;
  isRTL: boolean;
}
const getLocales = (): Locale[] => [
  { countryCode: 'US', languageTag: 'en-US', languageCode: 'en', isRTL: false },
  { countryCode: 'EC', languageTag: 'es-EC', languageCode: 'es', isRTL: false },
];

const findBestAvailableLanguage = (): Partial<Locale> => ({
  languageTag: 'es',
  isRTL: false,
});

const getNumberFormatSettings = (): Record<string, unknown> => ({
  decimalSeparator: '.',
  groupingSeparator: ',',
});

const getCalendar = (): string => 'gregorian'; // or "japanese", "buddhist"
const getCountry = (): string => 'ES'; // the country code you want
const getCurrencies = (): [string] => ['USD']; // can be empty array
const getTemperatureUnit = (): string => 'celsius'; // or "fahrenheit"
const getTimeZone = (): string => 'Ecuador/Guayaquil'; // the timezone you want
const uses24HourClock = (): boolean => true;
const usesMetricSystem = (): boolean => true;

const addEventListener = jest.fn();
const removeEventListener = jest.fn();

export {
  findBestAvailableLanguage,
  getLocales,
  getNumberFormatSettings,
  getCalendar,
  getCountry,
  getCurrencies,
  getTemperatureUnit,
  getTimeZone,
  uses24HourClock,
  usesMetricSystem,
  addEventListener,
  removeEventListener,
};
