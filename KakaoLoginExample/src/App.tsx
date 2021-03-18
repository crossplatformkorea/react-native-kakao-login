import React from 'react';
import RootNavigator from './components/navigations/RootStackNavigator';
import RootProvider from './providers';
import {initFbt} from './utils/fbt';

initFbt();

function App(): React.ReactElement {
  return <RootNavigator />;
}

function ProviderWrapper(): React.ReactElement {
  return (
    <RootProvider>
      <App />
    </RootProvider>
  );
}

export default ProviderWrapper;
