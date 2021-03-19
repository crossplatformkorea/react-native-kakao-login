// import {GlobalWithFetchMock} from 'jest-fetch-mock';
import {initFbt} from '../src/utils/fbt';
/**
 * monkey patching the locale to avoid the error:
 * Something went wrong initializing the native ReactLocalization module
 * https://gist.github.com/MoOx/08b465c3eac9e36e683929532472d1e0
 */
import mockAsyncStorage from '@react-native-community/async-storage/jest/async-storage-mock';

jest.mock('@react-native-community/async-storage', () => mockAsyncStorage);

jest.mock('react-native-reanimated', () =>
  require('react-native-reanimated/mock'),
);

// const customGlobal: any = global;

// customGlobal.fetch = require('jest-fetch-mock');
// customGlobal.fetchMock = customGlobal.fetch;

initFbt();
