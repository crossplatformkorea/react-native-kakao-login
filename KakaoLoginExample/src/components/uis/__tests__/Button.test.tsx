import 'react-native';

import * as React from 'react';

import {
  RenderAPI,
  act,
  cleanup,
  fireEvent,
  render,
} from '@testing-library/react-native';

import Button from '../Button';
import {ThemeType} from 'dooboo-ui';
import {createTestElement} from '../../../../test/testUtils';

// Note: test renderer must be required after react-native.

let props: any;
let component: React.ReactElement;
let testingLib: RenderAPI;

describe('[Button]', () => {
  let cnt = 1;

  beforeEach(() => {
    props = {
      onPress: (): number => cnt++,
      testID: 'btn',
    };

    component = createTestElement(<Button {...props} />);
  });

  afterEach(cleanup);

  it('[ThemeType.Light] renders without crashing', () => {
    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });

  it('should render [ThemeType.Dark] without crashing', () => {
    component = createTestElement(<Button {...props} />, ThemeType.DARK);

    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });

  it('should render [isDisabled] status without crashing', () => {
    props.isDisabled = true;
    component = createTestElement(<Button {...props} />);

    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });

  it('should render [isLoading] status without crashing', () => {
    props.isLoading = true;
    component = createTestElement(<Button {...props} />);

    testingLib = render(component);

    const baseElement = testingLib.toJSON();

    expect(baseElement).toMatchSnapshot();
    expect(baseElement).toBeTruthy();
  });

  describe('interactions', () => {
    beforeEach(() => {
      testingLib = render(component);
    });

    afterEach(cleanup);

    it('should simulate onClick', () => {
      const btn = testingLib.queryByTestId('btn');

      act(() => {
        fireEvent.press(btn);
        fireEvent.press(btn);
      });

      expect(cnt).toBe(3);
    });
  });
});
