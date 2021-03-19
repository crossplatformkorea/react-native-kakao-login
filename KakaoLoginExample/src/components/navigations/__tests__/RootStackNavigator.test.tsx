import 'react-native';

import React, {ReactElement} from 'react';
import {RenderAPI, cleanup, render} from '@testing-library/react-native';
import {createTestElement, createTestProps} from '../../../../test/testUtils';

import StackNavigator from '../RootStackNavigator';
import {ThemeType} from 'dooboo-ui';

let props: any;
let component: ReactElement;
let testingLib: RenderAPI;

describe('[Stack] navigator', () => {
  beforeEach(() => {
    props = createTestProps();

    component = createTestElement(<StackNavigator {...props} />);
  });

  afterEach(cleanup);

  it('should renders without crashing', () => {
    jest.useFakeTimers();

    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    jest.runAllTimers();
    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });

  it('should renders [Dark] mode', () => {
    jest.useFakeTimers();

    component = createTestElement(
      <StackNavigator {...props} />,
      ThemeType.DARK,
    );

    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    jest.runAllTimers();
    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });
});
