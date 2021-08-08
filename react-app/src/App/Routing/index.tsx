import { Component } from "react";
import {
    BrowserRouter,
    Switch,
    Route,
} from "react-router-dom";
import Auth from "../Auth";
import Navigation from "../Navigation";
import PlayerChars from "../PlayerChars";
import Views from "../Views";

interface Props {
}

interface AuthState {
    isSignedIn: boolean;
    username: string;

}

interface State {
    auth: AuthState;
}

const authTokenKey = 'p2e-tmp-auth';


class Routing extends Component<Props, State> {
    componentDidMount(): void {
        this.setAuthState();
    }

    setAuthState(): void {
        const username = sessionStorage.getItem(authTokenKey);
        this.setState({ ...this.state, auth: { isSignedIn: !!username, username: username || '' } });
    };

    onSignOn(username: string): void {
        sessionStorage.setItem(authTokenKey, username);
        this.setAuthState();
    };

    onSignOut(): void {
        sessionStorage.removeItem(authTokenKey);
        this.setAuthState();
    };

    render() {
        return <BrowserRouter>
            <Navigation authState={this.state?.auth} onSignOut={this.onSignOut.bind(this)}>
                {!this.state?.auth?.isSignedIn ?
                    <Auth onSignOn={this.onSignOn.bind(this)} /> :
                    (
                        <Switch>
                            <Route path="/create"><Views view="ANCESTRY" /></Route>
                            <Route path="/"><PlayerChars /></Route>
                        </Switch>
                    )
                }
            </Navigation>
        </BrowserRouter >
    }
}

export default Routing;