import {NativeModules as RNNativeModules} from 'react-native';
import React from 'react';

jest.mock('@react-navigation/stack', () => ({
  ...jest.requireActual('@react-navigation/stack'),
  useHeaderHeight: () => 12,
}));

/**
 * Temporarily test files that resolves https://github.com/facebook/react-native/issues/27721
 */

jest.mock(
  'react-native/Libraries/Components/Touchable/TouchableOpacity.js',
  () => {
    const {View} = require('react-native');

    const MockTouchable = (props): ReactElement => {
      return <View {...props} />;
    };

    MockTouchable.displayName = 'TouchableOpacity';

    return MockTouchable;
  },
);

jest.mock(
  'react-native/Libraries/Components/Touchable/TouchableHighlight.js',
  () => {
    const {View} = require('react-native');

    const MockTouchable = (props): ReactElement => {
      return <View {...props} />;
    };

    MockTouchable.displayName = 'TouchableHighlight';

    return MockTouchable;
  },
);

/** react-native-gesture-handler */

RNNativeModules.UIManager = RNNativeModules.UIManager || {};
RNNativeModules.UIManager.RCTView = RNNativeModules.UIManager.RCTView || {};

RNNativeModules.RNGestureHandlerModule = RNNativeModules.RNGestureHandlerModule || {
  State: {BEGAN: 'BEGAN', FAILED: 'FAILED', ACTIVE: 'ACTIVE', END: 'END'},
  attachGestureHandler: jest.fn(),
  createGestureHandler: jest.fn(),
  dropGestureHandler: jest.fn(),
  updateGestureHandler: jest.fn(),
};

RNNativeModules.PlatformConstants = RNNativeModules.PlatformConstants || {
  forceTouchAvailable: false,
};
