import logo from './logo.svg';
import './App.css';

function App() {
  getData();
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

function getData() {
  fetch('http://localhost:8080/project-2e-ws/api/v1/todo/list', {mode: 'no-cors'})
  .then(response => console.log(response))
  
}

export default App;
