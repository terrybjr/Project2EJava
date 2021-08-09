import { ReactElement } from 'react';
import { Provider } from 'react-redux';
import store from '../redux/store';
import Routing from './Routing';

const App = (): ReactElement => {
  return (
    <Provider store={store}>
      <Routing />
    </Provider>
  );
};

export default App;
