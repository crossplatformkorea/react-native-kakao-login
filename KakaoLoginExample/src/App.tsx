import React from 'react';
import RootNavigator from './components/navigations/RootStackNavigator';

function App(): React.ReactElement {
  return <RootNavigator />;
}

function ProviderWrapper(): React.ReactElement {
  return <App />;
}

export default ProviderWrapper;
