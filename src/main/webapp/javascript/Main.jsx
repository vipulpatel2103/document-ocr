import React, { Component } from "react";
import ReactDOM from 'react-dom';
import FriendList from './FriendList';
import OCRResult from './OCRResult';
import '../css/Main.css';

class Main extends Component {
    constructor(props) {
        super(props)
        this.state = {
            friends: [],
            ocrResult: {}
        }
    }

    componentDidMount() {
        this.fetchFriends();
    }

    fetchFriends() {
        fetch("/api/friends")
            .then(res => res.json())
            .then(
                (response) => {
                    this.setState({
                        friends: response
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    handleSubmit(evt) {
        evt.preventDefault();
        fetch("/api/friends", {
            method: "POST",
            body: new FormData(evt.target)
        }).then((response) => {
                if (response.ok) {
                    this.fetchFriends();
                } else {
                    alert("Failed to create friend");
                }
            }
        ).catch((error) => {
            alert(error);
        });
        evt.target.reset();
        return false;
    }
    handleFileSubmit(evt) {
            evt.preventDefault();
            fetch("/doc/ocr/text", {
                method: "POST",
                body: new FormData(evt.target)
            })
            .then(response => response.json())
            .then((response) => {

                    this.setState({
                        ocrResult: response
                    });

                }
            ).catch((error) => {
                alert(error);
            });
            evt.target.reset();
            return false;
        }

    render() {
        return (
            <div id="main">
                <h1>My Best Friends</h1>
                <FriendList friends={this.state.friends}/>
                <form onSubmit={this.handleSubmit.bind(this)}>
                    <input id="name" name="name" type="text" placeholder="Enter name"/>
                    <button type='submit'>Create</button>
                </form>

                <OCRResult ocrResult={this.state.ocrResult}/>
                <form onSubmit={this.handleFileSubmit.bind(this)}>
                    <input id="file" name="file" type="file" placeholder="file upload"/>
                    <button type='submit'>Upload</button>
                </form>
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint')
);
