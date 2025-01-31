import os
from flask import Flask

def create_app():
    app = Flask(__name__, instance_relative_config=False)
    app.secret_key = os.getenv('SECRET_KEY', 'una_clave_secreta_unica_y_segura')
    with app.app_context():
        from routes.route import router
        app.register_blueprint(router)
    return app
