import React, { ReactElement, useState } from 'react';
import { Container } from '@material-ui/core';
import Auth from './Auth';
import Navigation from './Navigation';

type AuthState = {
  isSignedIn: boolean;
  username?: string;
};

const initialAuthState: AuthState = { isSignedIn: false };

const App = (): ReactElement => {
  const [authState, setAuthState] = useState(initialAuthState);

  const onSignOn = (username: string): void => {
    setAuthState({ isSignedIn: true, username });
  };

  const onSignOut = (): void => {
    setAuthState(initialAuthState);
  };

  return (
    <Navigation authState={authState} onSignOut={onSignOut}>
      <Container>{authState.isSignedIn ? <>App</> : <Auth onSignOn={onSignOn} />}</Container>
    </Navigation>
  );
};

export default App;
