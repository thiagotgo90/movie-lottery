import { instanceOf } from 'prop-types';
import { Cookies, withCookies } from 'react-cookie';
import React, { Component } from 'react';

import {
    Card, CardImg, CardText, CardBody,
    CardTitle, Button, Container, Table
} from 'reactstrap';

import { withRouter, Link } from 'react-router-dom';

class Movie extends Component {

    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    emptyItem = {
        title: "",
        poster:"",
        plot: "",
        ratings: []
    };

    constructor(props) {
        super(props);
        const { cookies } = props;
        this.state = {
            item: this.emptyItem,
            csrfToken: cookies.get('XSRF-TOKEN')
        };
    }

    async componentDidMount() {
        const movie = await fetch('/movie/random', { credentials: 'include' });
        
        if(movie.ok){
            this.setState({ item: await movie.json() });
        } else {
            this.setState({item: null})
        }
        
    }

    render() {
        const { item } = this.state;
        const title = <h2>Your Movie for Today</h2>;

        if(item == null) {
            return <Button outline color="primary"><Link to="/movie/random">Random Movie</Link></Button>

        }

        return <Container>
            <Card>
                <CardImg style={{width: "30%"}}  top  src={item.poster} alt="Card image cap" />
                <CardBody>
                    <CardTitle>{item.title}</CardTitle>
                    <CardText>{item.plot}</CardText>
                    <Table>
                        <thead>
                            <tr>
                                <th>Site</th>
                                <th>Rating</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                item.ratings.map((rating) => {
                                    return (
                                    <tr>
                                        <td>{rating.source}</td>
                                        <td>{rating.value}</td>
                                    </tr>
                                )})
                            }
                        </tbody>
                    </Table>
                </CardBody>
            </Card>
        </Container>
    }
}

export default withCookies(withRouter(Movie));