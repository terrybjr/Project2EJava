import React, { ChangeEvent, FormEvent, ReactElement, useState } from 'react';
import {
  Button,
  Container,
  createStyles,
  makeStyles,
  Paper,
  TextField,
  Theme,
} from '@material-ui/core';

type AuthForm = {
  username: string;
  password: string;
};

type Props = {
  onSignOn: (username: string) => void;
};

const initialAuthForm: AuthForm = {
  username: '',
  password: '',
};

const useStyles = makeStyles((theme: Theme) => {
  const margin = theme.spacing(1);
  const padding = theme.spacing(2);

  return createStyles({
    form: { display: 'flex', flexDirection: 'column', '& > *': { margin } },
    input: { display: 'flex', justifyContent: 'center' },
    paper: { padding },
  });
});

const Auth = (props: Props): ReactElement => {
  const classes = useStyles();
  const { onSignOn } = props;

  const [authForm, setAuthForm] = useState<AuthForm>(initialAuthForm);

  const onChange = (event: ChangeEvent<HTMLInputElement>): void => {
    const { id, value } = event.target;
    setAuthForm({ ...authForm, [id]: value });
  };

  const onSubmit = (event: FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    onSignOn(authForm.username);
  };

  return (
    <Paper className={classes.paper} elevation={2}>
      <form onSubmit={onSubmit}>
        <Container className={classes.form}>
          <div className={classes.input}>
            <TextField id="username" fullWidth label="Username" onChange={onChange} type="text" />
          </div>
          <div className={classes.input}>
            <TextField
              id="password"
              disabled
              fullWidth
              label="Password"
              onChange={onChange}
              type="password"
            />
          </div>
          <div className={classes.input}>
            <Button type="submit">Sign In</Button>
          </div>
        </Container>
      </form>
    </Paper>
  );
};

export default Auth;
