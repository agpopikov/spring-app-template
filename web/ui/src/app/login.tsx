import * as React from 'react';

export interface LoginProps {
    onLogin: () => void;
}

export default function Login(props: LoginProps) {
    return (<button onClick={props.onLogin}>Login!</button>);
}
