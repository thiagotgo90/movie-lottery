import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import {CookiesProvider} from 'react-cookie'

import Home from './Home';
import Movie from './movie/Movie';

class App extends Component {
  render() {
    return (
      <Router>
        <CookiesProvider>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/movie/random' exact={true} component={Movie}/>
        </CookiesProvider>
      </Router>
    )
  }
}

export default App;