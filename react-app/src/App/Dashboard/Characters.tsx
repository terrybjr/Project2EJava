import {
  Avatar,
  Card,
  CardActionArea,
  CardHeader,
  Container,
  Grid,
  Typography,
} from '@material-ui/core';
import { Component } from 'react';
import { Link } from 'react-router-dom';
import { Character } from '../../models/Character.model';

type Props = {};

type State = {
  characters: Character[];
  index: number;
};

class Characters extends Component<Props, State> {
  componentDidMount() {
    this.setState({
      characters: [
        { id: '3ali-9a83-aos8-3a8n', name: 'Finrod', class: 'Wizard', level: 5 },
        { id: '2ati-9a82-tos8-2atn', name: 'Melda', class: 'Medic', level: 7 },
        { id: '5ati-9a85-tos8-5atn', name: 'Quinn', class: 'Ranger', level: 3 },
      ],
      index: 100,
    });
  }

  render() {
    return (
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h6">Characters</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={2}>
            {this.state?.characters?.map((character, index) =>
              index < 2 ? (
                <Grid item xs={4} key={character.id}>
                  <Link className="btn-link" to={`/character/${character.id}`}>
                    <Card>
                      <CardActionArea>
                        <CardHeader
                          avatar={<Avatar>{character.name[0]}</Avatar>}
                          aria-label={character.name}
                          title={character.name}
                          subheader={`Level ${character.level} ${character.class}`}
                        ></CardHeader>
                      </CardActionArea>
                    </Card>
                  </Link>
                </Grid>
              ) : (
                <></>
              )
            )}
            <Grid item xs={4}>
              <Link className="btn-link" to="/character/new">
                <Card>
                  <CardActionArea>
                    <CardHeader
                      avatar={<Avatar>+</Avatar>}
                      aria-label="new-character"
                      title="New Character"
                      subheader="Level 1"
                    ></CardHeader>
                  </CardActionArea>
                </Card>
              </Link>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    );
  }
}

export default Characters;
