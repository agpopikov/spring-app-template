import * as React from 'react'
import {render} from 'react-dom'
import {Redirect, Route} from 'react-router';
import {HashRouter} from 'react-router-dom';
import Main from './app/main';
import Login from './app/login';
import {Component, useContext, useState} from 'react';

export interface Auth {
    authenticated: boolean;
    name: string;
}

export const AuthContext = React.createContext({authenticated: true, name: ''} as Auth);

const ProtectedRoute = ({component: Component, ...rest}) => {
    const auth = useContext(AuthContext);
    return (<Route {...rest} render={(props) => (
        auth.authenticated
            ? <Component {...props} />
            : <Redirect to='/login'/>
    )}/>);
}


function App() {
    const [auth, setAuth] = useState({authenticated: false, name: ''} as Auth);
    return (
        <AuthContext.Provider value={auth}>
            <div>
                <HashRouter>
                    <Route path="/login" component={() => <Login onLogin={() =>
                        setAuth(p => ({...p, authenticated: true}))
                    }/>}/>
                    <ProtectedRoute path="/" component={() => <Main/>}/>
                </HashRouter>
            </div>
        </AuthContext.Provider>
    );
}

render(<App/>, document.getElementById('root'));
