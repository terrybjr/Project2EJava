import React, { ReactElement, useState } from 'react';
import { Container } from '@material-ui/core';
import Auth from './Auth';
import Navigation from './Navigation';
import Views, { ViewTypes } from './Views';

type AuthState = {
  isSignedIn: boolean;
  username?: string;
};

const initialAuthState: AuthState = { isSignedIn: false };

const App = (): ReactElement => {
  const [authState, setAuthState] = useState(initialAuthState);
  const [view, setView] = useState<ViewTypes>('');

  const onSignOn = (username: string): void => {
    setAuthState({ isSignedIn: true, username });
  };

  const onSignOut = (): void => {
    setAuthState(initialAuthState);
  };

  const onSetView = (view: ViewTypes): void => {
    setView(view);
  };

  return (
    <Navigation authState={authState} onSignOut={onSignOut} onSetView={onSetView}>
      <Container>
        {authState.isSignedIn ? <Views view={view} /> : <Auth onSignOn={onSignOn} />}
      </Container>
    </Navigation>
  );
};

export default App;
